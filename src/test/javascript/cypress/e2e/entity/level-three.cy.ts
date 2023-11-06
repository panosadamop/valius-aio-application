import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('LevelThree e2e test', () => {
  const levelThreePageUrl = '/level-three';
  const levelThreePageUrlPattern = new RegExp('/level-three(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const levelThreeSample = {
    identifier: 'Future New',
    mafCategory: 'Concrete Buckinghamshire Shirt',
    weightingMaf: 'Berkshire',
    lowAttractivenessRangeMaf: 'Human',
    mediumAttractivenessRangeMaf: 'redundant',
    highAttractivenessRangeMaf: 'GB',
  };

  let levelThree;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/level-threes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/level-threes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/level-threes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (levelThree) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/level-threes/${levelThree.id}`,
      }).then(() => {
        levelThree = undefined;
      });
    }
  });

  it('LevelThrees menu should load LevelThrees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('level-three');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LevelThree').should('exist');
    cy.url().should('match', levelThreePageUrlPattern);
  });

  describe('LevelThree page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(levelThreePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LevelThree page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/level-three/new$'));
        cy.getEntityCreateUpdateHeading('LevelThree');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelThreePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/level-threes',
          body: levelThreeSample,
        }).then(({ body }) => {
          levelThree = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/level-threes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/level-threes?page=0&size=20>; rel="last",<http://localhost/api/level-threes?page=0&size=20>; rel="first"',
              },
              body: [levelThree],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(levelThreePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LevelThree page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('levelThree');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelThreePageUrlPattern);
      });

      it('edit button click should load edit LevelThree page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelThree');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelThreePageUrlPattern);
      });

      it('edit button click should load edit LevelThree page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelThree');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelThreePageUrlPattern);
      });

      it('last delete button click should delete instance of LevelThree', () => {
        cy.intercept('GET', '/api/level-threes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('levelThree').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelThreePageUrlPattern);

        levelThree = undefined;
      });
    });
  });

  describe('new LevelThree page', () => {
    beforeEach(() => {
      cy.visit(`${levelThreePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LevelThree');
    });

    it('should create an instance of LevelThree', () => {
      cy.get(`[data-cy="identifier"]`).type('FTP payment').should('have.value', 'FTP payment');

      cy.get(`[data-cy="mafCategory"]`).type('Assistant sticky').should('have.value', 'Assistant sticky');

      cy.get(`[data-cy="weightingMaf"]`).type('invoice haptic').should('have.value', 'invoice haptic');

      cy.get(`[data-cy="lowAttractivenessRangeMaf"]`).type('Beauty client-server').should('have.value', 'Beauty client-server');

      cy.get(`[data-cy="mediumAttractivenessRangeMaf"]`).type('Uzbekistan').should('have.value', 'Uzbekistan');

      cy.get(`[data-cy="highAttractivenessRangeMaf"]`)
        .type('Configuration complexity Electronics')
        .should('have.value', 'Configuration complexity Electronics');

      cy.get(`[data-cy="segmentScoreMaf"]`).type('Division Grocery').should('have.value', 'Division Grocery');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        levelThree = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', levelThreePageUrlPattern);
    });
  });
});
