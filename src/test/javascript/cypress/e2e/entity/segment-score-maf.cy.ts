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

describe('SegmentScoreMAF e2e test', () => {
  const segmentScoreMAFPageUrl = '/segment-score-maf';
  const segmentScoreMAFPageUrlPattern = new RegExp('/segment-score-maf(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const segmentScoreMAFSample = { value: 'Frozen', language: 'ENGLISH' };

  let segmentScoreMAF;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/segment-score-mafs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/segment-score-mafs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/segment-score-mafs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (segmentScoreMAF) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/segment-score-mafs/${segmentScoreMAF.id}`,
      }).then(() => {
        segmentScoreMAF = undefined;
      });
    }
  });

  it('SegmentScoreMAFS menu should load SegmentScoreMAFS page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('segment-score-maf');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SegmentScoreMAF').should('exist');
    cy.url().should('match', segmentScoreMAFPageUrlPattern);
  });

  describe('SegmentScoreMAF page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(segmentScoreMAFPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SegmentScoreMAF page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/segment-score-maf/new$'));
        cy.getEntityCreateUpdateHeading('SegmentScoreMAF');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentScoreMAFPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/segment-score-mafs',
          body: segmentScoreMAFSample,
        }).then(({ body }) => {
          segmentScoreMAF = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/segment-score-mafs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/segment-score-mafs?page=0&size=20>; rel="last",<http://localhost/api/segment-score-mafs?page=0&size=20>; rel="first"',
              },
              body: [segmentScoreMAF],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(segmentScoreMAFPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SegmentScoreMAF page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('segmentScoreMAF');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentScoreMAFPageUrlPattern);
      });

      it('edit button click should load edit SegmentScoreMAF page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SegmentScoreMAF');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentScoreMAFPageUrlPattern);
      });

      it('edit button click should load edit SegmentScoreMAF page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SegmentScoreMAF');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentScoreMAFPageUrlPattern);
      });

      it('last delete button click should delete instance of SegmentScoreMAF', () => {
        cy.intercept('GET', '/api/segment-score-mafs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('segmentScoreMAF').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentScoreMAFPageUrlPattern);

        segmentScoreMAF = undefined;
      });
    });
  });

  describe('new SegmentScoreMAF page', () => {
    beforeEach(() => {
      cy.visit(`${segmentScoreMAFPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SegmentScoreMAF');
    });

    it('should create an instance of SegmentScoreMAF', () => {
      cy.get(`[data-cy="value"]`).type('parse Account Rue').should('have.value', 'parse Account Rue');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        segmentScoreMAF = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', segmentScoreMAFPageUrlPattern);
    });
  });
});
