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

describe('LevelTwo e2e test', () => {
  const levelTwoPageUrl = '/level-two';
  const levelTwoPageUrlPattern = new RegExp('/level-two(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const levelTwoSample = {
    identifier: 'Georgia',
    targetMarket: 'primary',
    currentMarketSegmentation: 'integrated Garden De-engineered',
    segmentName: 'Granite Man',
    segmentDescription: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    buyingCriteriaCategory: 'deposit',
    competitorProductName: 'value-added',
    competitorCompanyName: 'Officer Practical Communications',
    competitorBrandName: 'web-enabled',
    competitorProductDescription: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    competitorMaturityPhase: 'withdrawal Directives B2B',
    competitorCompetitivePosition: 'HTTP',
  };

  let levelTwo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/level-twos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/level-twos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/level-twos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (levelTwo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/level-twos/${levelTwo.id}`,
      }).then(() => {
        levelTwo = undefined;
      });
    }
  });

  it('LevelTwos menu should load LevelTwos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('level-two');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LevelTwo').should('exist');
    cy.url().should('match', levelTwoPageUrlPattern);
  });

  describe('LevelTwo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(levelTwoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LevelTwo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/level-two/new$'));
        cy.getEntityCreateUpdateHeading('LevelTwo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelTwoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/level-twos',
          body: levelTwoSample,
        }).then(({ body }) => {
          levelTwo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/level-twos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/level-twos?page=0&size=20>; rel="last",<http://localhost/api/level-twos?page=0&size=20>; rel="first"',
              },
              body: [levelTwo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(levelTwoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LevelTwo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('levelTwo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelTwoPageUrlPattern);
      });

      it('edit button click should load edit LevelTwo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelTwo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelTwoPageUrlPattern);
      });

      it('edit button click should load edit LevelTwo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelTwo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelTwoPageUrlPattern);
      });

      it('last delete button click should delete instance of LevelTwo', () => {
        cy.intercept('GET', '/api/level-twos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('levelTwo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelTwoPageUrlPattern);

        levelTwo = undefined;
      });
    });
  });

  describe('new LevelTwo page', () => {
    beforeEach(() => {
      cy.visit(`${levelTwoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LevelTwo');
    });

    it('should create an instance of LevelTwo', () => {
      cy.get(`[data-cy="identifier"]`).type('Associate').should('have.value', 'Associate');

      cy.get(`[data-cy="targetMarket"]`)
        .type('Electronics Administrator Account')
        .should('have.value', 'Electronics Administrator Account');

      cy.get(`[data-cy="currentMarketSegmentation"]`).type('Radial Costa').should('have.value', 'Radial Costa');

      cy.get(`[data-cy="segmentName"]`).type('transmitting Buckinghamshire').should('have.value', 'transmitting Buckinghamshire');

      cy.get(`[data-cy="marketSegmentationType"]`).type('Plastic analyzing schemas').should('have.value', 'Plastic analyzing schemas');

      cy.get(`[data-cy="uniqueCharacteristic"]`).type('Agent deposit').should('have.value', 'Agent deposit');

      cy.get(`[data-cy="segmentDescription"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="buyingCriteriaCategory"]`).type('Trace Frozen').should('have.value', 'Trace Frozen');

      cy.get(`[data-cy="competitorProductName"]`).type('quantifying Future-proofed').should('have.value', 'quantifying Future-proofed');

      cy.get(`[data-cy="competitorCompanyName"]`).type('Intelligent Cambridgeshire').should('have.value', 'Intelligent Cambridgeshire');

      cy.get(`[data-cy="competitorBrandName"]`).type('Computer navigate software').should('have.value', 'Computer navigate software');

      cy.get(`[data-cy="competitorProductDescription"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="competitorMaturityPhase"]`).type('driver Metal').should('have.value', 'driver Metal');

      cy.get(`[data-cy="competitorCompetitivePosition"]`).type('connecting TCP').should('have.value', 'connecting TCP');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        levelTwo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', levelTwoPageUrlPattern);
    });
  });
});
